package org.code2bytes.samples.lambda;

import static org.junit.Assert.assertEquals;

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
		assertEquals("Array should contain 4 elements", 4, persons.size());
	}

	@Test
	public void testForEach() {
		persons.forEach(p -> System.out.println(p.getAgeInYears()));
	}
}
