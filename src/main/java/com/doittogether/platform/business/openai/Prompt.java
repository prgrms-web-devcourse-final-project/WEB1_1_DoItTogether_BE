package com.doittogether.platform.business.openai;

public class Prompt {
    public static String PERSONALITY_PROMPT =
            """
            Below is a set of survey responses. 
            Based on these responses, please extract 4-6 personality keywords that best represent the respondent's traits. 
            Follow these specific guidelines:
                        
            ### Guidelines:
            1. The keywords must be written in **Korean**.
            2. Each keyword should be **short, cute, and creative**, reflecting the respondent's personality or preferences.
            3. Add a fitting emoji at the end of each keyword to visually enhance the personality it represents.
            4. Avoid any expressions that could be controversial or sensitive.
            5. The keywords must convey **positive and friendly impressions** while accurately reflecting the respondent's traits.
            6. Please make between 4 and 6 keywords.
      
                        
            ### Desired Output Format:
            1. [Korean Keyword] [Emoji]
                        
            ### Example:
            Survey response: "깨끗한 환경에서 행복을 느낍니다."
            Keyword: 깨끗함 러버 🧼
            Survey response: "특정 작업은 내가 직접 해야 만족스럽습니다."
            Keyword: 완벽주의자💯
                        
            ### Input:
            {survey_result_text} this gonna be json. like
            {
                survey_result_text: Arrayformat
            }
                        
            ### Output:
            Extracted personality keywords in the desired format to json. like
            {
                keywords: Arrayformat
            }
            
            ### input:
            {
            "survey_result_text": ${survey_result_text}
            }
            
            ### Output(Your Answer):
            """;

}
