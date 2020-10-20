package com.blackchopper.demo_onlite;

import com.hacknife.onlite.BinaryCondition;
import com.hacknife.onlite.Condition;
import com.hacknife.onlite.EmptyCondition;
import com.hacknife.onlite.OpCondition;

import java.util.Arrays;

import static com.hacknife.onlite.BinaryCondition.Operator.EQUAL;
import static com.hacknife.onlite.BinaryCondition.Operator.GREATER_THAN;

public class Test {

    public static void main(String[] a) {
        Condition condition = new OpCondition()
                .setLeftCondition(new OpCondition()
                        .setLeftCondition(new BinaryCondition("name", GREATER_THAN, "chair"))
                        .setOperator(OpCondition.Operator.OR)
                        .setRightCondition(new BinaryCondition("name", EQUAL, "cccc")))
                .setOperator(OpCondition.Operator.AND);
        System.out.println(condition.whereClause());
        System.out.println(Arrays.toString(condition.whereArgs()));
    }
}
