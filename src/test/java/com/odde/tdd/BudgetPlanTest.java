package com.odde.tdd;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetPlanTest {

    @Test
    public void no_budget(){
        BudgetRepo repo = mock(BudgetRepo.class);
        when(repo.findAll()).thenReturn(Collections.emptyList());
        BudgetPlan plan = new BudgetPlan(repo);
        assertEquals(0, plan.query(LocalDate.of(2023,3,20), LocalDate.of(2023, 4,14)));
    }

    @Test
    public void full_month_budget(){
        BudgetRepo repo = mock(BudgetRepo.class);
        when(repo.findAll()).thenReturn(prepareBudget());
        BudgetPlan plan = new BudgetPlan(repo);
        assertEquals(3100, plan.query(LocalDate.of(2023,5,1), LocalDate.of(2023, 5,31)));
    }

    @Test
    public void part_start_month_budget(){
        BudgetRepo repo = mock(BudgetRepo.class);
        when(repo.findAll()).thenReturn(prepareBudget());
        BudgetPlan plan = new BudgetPlan(repo);
        assertEquals(1200, plan.query(LocalDate.of(2023,5,20), LocalDate.of(2023, 5,31)));
    }

    @Test
    public void part_end_month_budget(){
        BudgetRepo repo = mock(BudgetRepo.class);

        when(repo.findAll()).thenReturn(prepareBudget());
        BudgetPlan plan = new BudgetPlan(repo);
        assertEquals(2800, plan.query(LocalDate.of(2023,7,1), LocalDate.of(2023, 7,14)));
    }

    @Test
    public void full_during_budget(){
        BudgetRepo repo = mock(BudgetRepo.class);

        when(repo.findAll()).thenReturn(prepareBudget());
        BudgetPlan plan = new BudgetPlan(repo);
        assertEquals(4300, plan.query(LocalDate.of(2023,5,20), LocalDate.of(2023, 7,14)));
    }

    public List<Budget> prepareBudget(){
        List<Budget> budgets = new ArrayList<Budget>();
        budgets.add(new Budget(YearMonth.of(2023,4), 600));
        budgets.add(new Budget(YearMonth.of(2023,5), 3100));
        budgets.add(new Budget(YearMonth.of(2023,6), 300));
        budgets.add(new Budget(YearMonth.of(2023,7), 6200));
        budgets.add(new Budget(YearMonth.of(2023,8), 620));
        return budgets;
    }
}
