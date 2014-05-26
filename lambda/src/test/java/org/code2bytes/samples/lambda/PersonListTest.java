package org.code2bytes.samples.lambda;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

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
		assertThat("array contains 4 elements", 4, equalTo(4));
	}

	@Test
	public void testSum() {
		Integer total = persons.parallelStream().map(p -> p.getAgeInYears())
				.reduce((sum, p) -> sum + p).get();
		assertThat("sum should be 86", 86, equalTo(total));
	}
}
