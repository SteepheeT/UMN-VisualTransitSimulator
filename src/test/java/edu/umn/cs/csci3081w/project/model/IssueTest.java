package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IssueTest {

  private Issue testIssue;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    testIssue = new Issue();
  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(0, testIssue.getCounter());
  }

  /**
   * Test decrement counter with 10 times.
   */
  @Test
  public void testDecrementCounter() {
    assertEquals(0, testIssue.getCounter());
    testIssue.createIssue();
    assertEquals(10, testIssue.getCounter());
    testIssue.decrementCounter();
    assertEquals(9, testIssue.getCounter());
    for (int i = 0; i < 5; i++) {
      testIssue.decrementCounter();
    }
    assertEquals(4, testIssue.getCounter());
  }

  /**
   * Test createIssue with two time.
   */
  @Test
  public void testCreateIssue() {
    testIssue.createIssue();
    assertEquals(10, testIssue.getCounter());
    testIssue.createIssue();
    assertEquals(10, testIssue.getCounter());
  }

  /**
   * Test if issue resovled method when have an issue.
   */
  @Test
  public void testIsIssueResolved() {
    assertEquals(true, testIssue.isIssueResolved());
    testIssue.createIssue();
    assertEquals(false, testIssue.isIssueResolved());
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testIssue = null;
  }

}