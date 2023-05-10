package com.odde.tdd;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.List;

public class BudgetPlan {

    private final BudgetRepo repo;

    public BudgetPlan(BudgetRepo repo) {
        this.repo = repo;
    }

    public double query(LocalDate start, LocalDate end){
        List<Budget> budgets = repo.findAll();
        double finalBudget = 0.0;

        if (budgets.isEmpty()){
            return finalBudget;
        }

        YearMonth startYearMonth = YearMonth.from(start);
        YearMonth endYearMonth = YearMonth.from(end);

        int startMonthNonBudget = start.getDayOfMonth() - 1;
        int endMonthNonBudget = end.lengthOfMonth() - end.getDayOfMonth();

        int startMonthIndex = 0;
        int endMonthIndex = budgets.size();

        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getMonth().equals(startYearMonth)){
                startMonthIndex = i;
            }
            if (budgets.get(i).getMonth().equals(endYearMonth)){
                endMonthIndex = i;
                break;
            }
        }

        for (int i = startMonthIndex; i <= endMonthIndex; i++){
            finalBudget += budgets.get(i).getAmount();
        }

        finalBudget -= budgets.get(startMonthIndex).getAmount() / start.lengthOfMonth() * startMonthNonBudget;
        finalBudget -= budgets.get(endMonthIndex).getAmount() /end.lengthOfMonth() * endMonthNonBudget;

        return finalBudget;
    }

}
