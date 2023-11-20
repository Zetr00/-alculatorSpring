package com.example.firstpract.controller;

// AppController.java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    private final Map<String, Double> conversionRates = new HashMap<>();

    public HomeController() {
        conversionRates.put("USD", 1.0);
        conversionRates.put("EUR", 0.95);
        conversionRates.put("RUB", 90.0);
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/calculator")
    public String showCalculatorPage() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("num1") double num1,
                            @RequestParam("num2") double num2,
                            @RequestParam("operation") String operation,
                            Model model) {
        double result = 0;
        switch (operation) {
            case "add":
                result = num1 + num2;
                break;
            case "subtract":
                result = num1 - num2;
                break;
            case "multiplication":
                result = num1 * num2;
                break;
            case "division":
                result = num1 / num2;
                break;
        }

        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/currencyConverter")
    public String showCurrencyConverterPage() {
        return "currencyConverter";
    }

    @PostMapping("/convertCurrency")
    public String convertCurrency(@RequestParam("fromCurrency") String fromCurrency,
                                  @RequestParam("toCurrency") String toCurrency,
                                  @RequestParam("amount") double amount,
                                  Model model) {

        double convertedAmount = amount * conversionRate(fromCurrency, toCurrency);

        model.addAttribute("convertedAmount", convertedAmount);
        return "currencyConversionResult";
    }

    private double conversionRate(String fromCurrency, String toCurrency) {
        double fromRate = conversionRates.getOrDefault(fromCurrency, 1.0);
        double toRate = conversionRates.getOrDefault(toCurrency, 1.0);

        if (fromRate == 0 || toRate == 0) {
            throw new IllegalArgumentException("Ошибка");
        }

        return toRate / fromRate;
    }
}

