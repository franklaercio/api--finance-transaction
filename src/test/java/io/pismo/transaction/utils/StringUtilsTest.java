package io.pismo.transaction.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StringUtilsTest {

  @Test
  void should_given_true_when_string_is_blank() {
    assertTrue(StringUtils.isBlank(null));
    assertTrue(StringUtils.isBlank(""));
    assertTrue(StringUtils.isBlank(" "));
  }

  @Test
  void should_given_true_when_string_is_a_number() {
    assertFalse(StringUtils.isNotNumberOrBlank("1"));
  }

  @Test
  void should_given_true_when_string_is_null() {
    assertTrue(StringUtils.isNotNumberOrBlank(null));
  }

  @Test
  void should_given_true_when_string_is_not_number() {
    assertTrue(StringUtils.isNotNumberOrBlank("a2"));
  }

}
