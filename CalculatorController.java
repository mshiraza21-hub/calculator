package com.calculator;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calculate")
public class CalculatorController {

    @PostMapping
    public Map<String, Object> calculate(
            @RequestParam double num1,
            @RequestParam double num2,
            @RequestParam String operator) {

        Map<String, Object> response = new HashMap<>();

        try {
            double result;

            switch (operator) {
                case "+" -> result = num1 + num2;
                case "-" -> result = num1 - num2;
                case "*" -> result = num1 * num2;
                case "/" -> {
                    if (num2 == 0) {
                        response.put("error", "Cannot divide by zero");
                        return response;
                    }
                    result = num1 / num2;
                }
                default -> {
                    response.put("error", "Invalid operator");
                    return response;
                }
            }

            // Return clean integer if no decimal part
            if (result == Math.floor(result) && !Double.isInfinite(result)) {
                response.put("result", (long) result);
            } else {
                response.put("result", result);
            }

        } catch (Exception e) {
            response.put("error", "Server error: " + e.getMessage());
        }

        return response;
    }
}
