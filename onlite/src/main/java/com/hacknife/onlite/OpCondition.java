package com.hacknife.onlite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class OpCondition extends Condition {

    public enum Operator {
        AND("and"),
        OR("or");
        private final String operator;

        Operator(String operator) {
            this.operator = operator;
        }
    }

    private Operator operator = Operator.OR;
    private Condition left = new EmptyCondition();
    private Condition right = new EmptyCondition();

    public OpCondition() {
    }

    public OpCondition(Operator operator, Condition left, Condition right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public OpCondition setLeftCondition(Condition condition) {
        this.left = condition;
        return this;
    }

    public OpCondition setRightCondition(Condition condition) {
        this.right = condition;
        return this;
    }

    public OpCondition setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    @Override
    public String whereClause() {
        return String.format("(%s) %s (%s)",
                left.whereClause(),
                operator.operator,
                right.whereClause()
        );
    }

    @Override
    public String[] whereArgs() {
        List<String> args = new ArrayList<>();
        args.addAll(Arrays.asList(left.whereArgs()));
        args.addAll(Arrays.asList(right.whereArgs()));
        return args.toArray(new String[]{});
    }

}
