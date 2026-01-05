function validatePositiveNumber(value, fieldName) {
    if (value <= 0) {
        alert(fieldName + " must be greater than zero.");
        return false;
    }
    return true;
}

function validateExpenseForm() {
    const amount = document.getElementById("expenseAmount").value;
    const category = document.getElementById("expenseCategory").value;

    if (!validatePositiveNumber(amount, "Expense Amount")) return false;

    if (category.trim().length < 2) {
        alert("Category must have at least 2 characters.");
        return false;
    }
    return true;
}

function validateBudgetForm() {
    const amount = document.getElementById("budgetAmount").value;

    if (!validatePositiveNumber(amount, "Budget Amount")) return false;

    return true;
}

function validateGoalForm() {
    const amount = document.getElementById("goalAmount").value;

    if (!validatePositiveNumber(amount, "Target Amount")) return false;

    return true;
}
