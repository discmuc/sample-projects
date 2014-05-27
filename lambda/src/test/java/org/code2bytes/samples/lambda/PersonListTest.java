package org.code2bytes.samples.lambda;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

public class PersonListTest {

	private List<Person> persons;

	@Before
	public void setUp() throws Exception {
		persons = new ArrayList<>();
		persons.add(new Person("Test Person 1", 20));
		persons.add(new Person("Test Person 2", 21));
		persons.add(new Person("Test Person 3", 22));
		persons.add(new Person("Test Person 4", 23));
	}

	@Test
	public void testArraySize() {
		assertThat("array contains 4 elements", persons.size(), equalTo(4));
	}

	@Test
	public void testSumOfAges() {
		Integer total = persons.parallelStream().map(p -> p.getAgeInYears())
				.reduce((sum, p) -> sum + p).get();
		assertThat("sum should be 86", total, equalTo(86));
	}

	@Test
	public void testPersonList() {
		BinaryOperator<String> concat = (str, p) -> str = str + ", " + p;
		Optional<String> output = persons.parallelStream()
				.map(p -> p.getName()).reduce(concat);
		String expected = "Test Person 1, Test Person 2, Test Person 3, Test Person 4";
		assertThat("names should be concated", output.get(), equalTo(expected));
	}

	@Test
	public void testPredicateTerm() {
		Predicate<Integer> isOlderThan22 = age -> age > 22;
		boolean matchAll = persons.stream().map(p -> p.getAgeInYears())
				.allMatch(isOlderThan22);
		assertFalse("there are persons of age 22 or below", matchAll);
		boolean matchAny = persons.stream().map(p -> p.getAgeInYears())
				.anyMatch(isOlderThan22);
		assertTrue("there is at least one persons of age 23 or older", matchAny);
	}

	@Test
	public void testCountPersonsOverTwenty() {
		long count = persons.stream().filter(p -> (p.getAgeInYears() > 20))
				.count();
		assertThat("3 persons are over 20", count, equalTo(3L));
	}
}
