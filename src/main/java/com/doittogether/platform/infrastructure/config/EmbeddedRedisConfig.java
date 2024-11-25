package com.doittogether.platform.infrastructure.config;

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

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.maxmemory}")
    private String maxMemory;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
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
    }

    private int findAvailablePort() throws IOException {
        for (int port = 10000; port < 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }
        throw new RuntimeException("연결 가능한 port 를 찾을 수 없습니다.");
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private boolean isRedisRunning() throws IOException {
        Process process = executeGrepProcessCommand(port);
        return isRunning(process);
    }

    private Process executeGrepProcessCommand(int port) throws IOException {
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
    }

    private boolean isRunning(Process process) {
        StringBuilder pidInfo = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Redis 프로세스 확인 중 오류 발생", e);
        }
        return StringUtils.hasText(pidInfo.toString());
    }

    // mac os용 redis 바이너리 파일
    private File getRedisServerExecutable() {
        try {
            return new File("src/main/resources/redis/redis-server-7.2.6-mac-arm64");
        } catch (Exception e) {
            throw new RuntimeException("가용한 레디스 서버를 찾지 못했습니다.", e);
        }
    }

    // mac os 인지 확인
    private boolean isArmArchitecture() {
        String architecture = System.getProperty("os.arch");
        return architecture.contains("aarch64");
    }
}
