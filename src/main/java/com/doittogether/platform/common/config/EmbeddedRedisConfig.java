package com.doittogether.platform.common.config;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.redis.EmbeddedRedisException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    private final int port;

    private final String maxMemory;

    private RedisServer redisServer;

    public EmbeddedRedisConfig(
            @Value("${spring.data.redis.port}") int port,
            @Value("${spring.data.redis.maxmemory}") String maxMemory) {
        this.port = port;
        this.maxMemory = maxMemory;
    }

    @PostConstruct
    public void startRedis() {
        try {
            int embeddedRedisPort = port;
            if (isRedisRunning()) {
                embeddedRedisPort = findAvailablePort();
            }

            if (isArmArchitecture()) {
                File redisExecutable = getRedisServerExecutable();
                redisServer = new RedisServer(Objects.requireNonNull(redisExecutable), embeddedRedisPort);
            }

            if (!isArmArchitecture()) {
                redisServer = RedisServer.builder()
                        .port(embeddedRedisPort)
                        .setting("maxmemory " + maxMemory)
                        .build();
            }

            redisServer.start();
        } catch (Exception e) {
            throw new EmbeddedRedisException(ExceptionCode.EMBEDDED_REDIS_START_FAILED);
        }
    }

    private int findAvailablePort() {
        try {
            for (int port = 10000; port < 65535; port++) {
                Process process = executeGrepProcessCommand(port);
                if (!isRunning(process)) {
                    return port;
                }
            }
            throw new EmbeddedRedisException(ExceptionCode.EMBEDDED_REDIS_PORT_UNAVAILABLE);
        } catch (Exception e) {
            throw new EmbeddedRedisException(ExceptionCode.EMBEDDED_REDIS_PORT_UNAVAILABLE);
        }
    }

    @PreDestroy
    public void stopRedis() {
        try {
            if (redisServer != null) {
                redisServer.stop();
            }
        } catch (Exception e) {
            throw new EmbeddedRedisException(ExceptionCode.EMBEDDED_REDIS_STOP_FAILED);
        }
    }

    private boolean isRedisRunning() {
        Process process = executeGrepProcessCommand(port);
        return isRunning(process);
    }

    private Process executeGrepProcessCommand(int port) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String command;

            if (os.contains("win")) {
                command = String.format("netstat -nao | find \"LISTENING\" | find \":%d\"", port);
                String[] cmd = {"cmd.exe", "/y", "/c", command};
                return Runtime.getRuntime().exec(cmd);
            }

            command = String.format("netstat -nat | grep LISTEN | grep %d", port);
            String[] shell = {"/bin/sh", "-c", command};
            return Runtime.getRuntime().exec(shell);
        } catch (IOException e) {
            throw new EmbeddedRedisException(ExceptionCode.EMBEDDED_REDIS_PROCESS_CHECK_FAILED);
        }
    }

    private boolean isRunning(Process process) {
        StringBuilder pidInfo = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception e) {
            throw new EmbeddedRedisException(ExceptionCode.EMBEDDED_REDIS_PROCESS_CHECK_FAILED);
        }
        return StringUtils.hasText(pidInfo.toString());
    }

    // mac os용 redis 바이너리 파일
    private File getRedisServerExecutable() {
        try {
            return new File("/opt/homebrew/bin/redis-server");
        } catch (Exception e) {
            throw new EmbeddedRedisException(ExceptionCode.EMBEDDED_REDIS_EXECUTABLE_NOT_FOUND);
        }
    }

    // mac os 인지 확인
    private boolean isArmArchitecture() {
        String architecture = System.getProperty("os.arch");
        return architecture.contains("aarch64");
    }
}
