package org.softRoad;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.softRoad.models.query.*;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
public class QueryUtilsTest {

    @Test
    public void getConditionHqlQueryTest(){
        SimpleCondition test1 = new SimpleCondition("A", 1, SimpleCondition.Operator.EQUAL);
        SearchConditionHqlQuery sqlResult1 = QueryUtils.getConditionHqlQuery(test1, null);
        Assertions.assertEquals(" A = :A_0", sqlResult1.getSql());
        Assertions.assertEquals(1, sqlResult1.getParams().get("A_0"));

        SimpleCondition test2 = new SimpleCondition("B", "temp", SimpleCondition.Operator.LIKE);
        SearchConditionHqlQuery sqlResult2 = QueryUtils.getConditionHqlQuery(test2, null);
        Assertions.assertEquals(" B like :B_0", sqlResult2.getSql());
        Assertions.assertEquals("%temp%", sqlResult2.getParams().get("B_0"));

        SimpleCondition test3 = new SimpleCondition("C", 0x55, SimpleCondition.Operator.GTE);
        SearchConditionHqlQuery sqlResult3 = QueryUtils.getConditionHqlQuery(test3, null);
        Assertions.assertEquals(" C >= :C_0", sqlResult3.getSql());
        Assertions.assertEquals(0x55, sqlResult3.getParams().get("C_0"));

        List<SearchCondition> conditionsTest4 = new ArrayList<>();
        conditionsTest4.add(test1);
        conditionsTest4.add(test2);
        conditionsTest4.add(test3);
        ComplexCondition test4 = new ComplexCondition(conditionsTest4, ComplexCondition.Operator.OR);
        SearchConditionHqlQuery sqlResult4 = QueryUtils.getConditionHqlQuery(test4, null);
        Assertions.assertEquals("( A = :A_0 OR  B like :B_1 OR  C >= :C_2)", sqlResult4.getSql());
        Assertions.assertEquals(1, sqlResult4.getParams().get("A_0"));
        Assertions.assertEquals("%temp%", sqlResult4.getParams().get("B_1"));
        Assertions.assertEquals(0x55, sqlResult4.getParams().get("C_2"));

        SimpleCondition test5 = new SimpleCondition("B", '8', SimpleCondition.Operator.LT);
        SearchConditionHqlQuery sqlResult5 = QueryUtils.getConditionHqlQuery(test5, null);
        Assertions.assertEquals(" B < :B_0", sqlResult5.getSql());
        Assertions.assertEquals('8', sqlResult5.getParams().get("B_0"));

        SimpleCondition test6 = new SimpleCondition("D", 0b011, SimpleCondition.Operator.LTE);
        SearchConditionHqlQuery sqlResult6 = QueryUtils.getConditionHqlQuery(test6, null);
        Assertions.assertEquals(" D <= :D_0", sqlResult6.getSql());
        Assertions.assertEquals(0b011, sqlResult6.getParams().get("D_0"));

        List<SearchCondition> conditionsTest7 = new ArrayList<>();
        conditionsTest7.add(test4);
        conditionsTest7.add(test5);
        conditionsTest7.add(test6);
        ComplexCondition test7 = new ComplexCondition(conditionsTest7, ComplexCondition.Operator.AND);
        SearchConditionHqlQuery sqlResult7 = QueryUtils.getConditionHqlQuery(test7, null);
        Assertions.assertEquals("(( A = :A_0 OR  B like :B_1 OR  C >= :C_2) AND  B < :B_3 AND  D <= :D_4)", sqlResult7.getSql());
        Assertions.assertEquals(1, sqlResult7.getParams().get("A_0"));
        Assertions.assertEquals("%temp%", sqlResult7.getParams().get("B_1"));
        Assertions.assertEquals(0x55, sqlResult7.getParams().get("C_2"));
        Assertions.assertEquals('8', sqlResult7.getParams().get("B_3"));
        Assertions.assertEquals(0B011, sqlResult7.getParams().get("D_4"));
    }
}
