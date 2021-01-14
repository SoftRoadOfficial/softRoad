package org.softRoad;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.softRoad.utils.Validators.nationalIDValidator;

@QuarkusTest
public class ValidatorsTest {
    @Test
    public void nationalIDValidatorTest(){
        Assertions.assertTrue(nationalIDValidator("2282758099"));
        Assertions.assertTrue(nationalIDValidator("2282673311"));
        Assertions.assertFalse(nationalIDValidator("2282645482"));
    }
}
