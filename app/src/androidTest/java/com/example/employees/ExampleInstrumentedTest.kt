package com.example.employees

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.employees.model.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    val goodDataEmployee = Employee(
        id = "id",
        fullName = "name",
        phoneNumber = "5555555555",
        emailAddress = "email@email.com",
        biography = "bio",
        photoUrlSmall = null,
        photoUrlLarge = null,
        team = "TEAM",
        employeeType = EmployeeType.CONTRACTOR
    )

    val badDataEmployee = Employee(
        id = "id",
        fullName = "name",
        phoneNumber = "badPhoneNumber234",
        emailAddress = "email",
        biography = "bio",
        photoUrlSmall = null,
        photoUrlLarge = null,
        team = "team",
        employeeType = EmployeeType.CONTRACTOR
    )

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.employees", appContext.packageName)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testBadPhoneNumberIsNotValid() {
        assertEquals(badDataEmployee.hasValidOrEmptyPhoneNumber(), false)
    }

    @Test
    fun testGoodPhoneNumberIsValid() {
        assertEquals(goodDataEmployee.hasValidOrEmptyPhoneNumber(), true)
    }

    @Test
    fun testBadEmailIsNotValid() {
        val badDataEmployee = Employee(
            id = "id",
            fullName = "name",
            phoneNumber = "badPhoneNumber234",
            emailAddress = "email",
            biography = "bio",
            photoUrlSmall = null,
            photoUrlLarge = null,
            team = "team",
            employeeType = EmployeeType.CONTRACTOR
        )
        assertEquals(badDataEmployee.hasValidEmail(), false)
    }

    @Test
    fun testGoodEmailIsValid() {
        assertEquals(goodDataEmployee.hasValidEmail(), true)
    }

    @Test
    fun testGoodTypeIsValid() {
        assertEquals(goodDataEmployee.hasValidEmployeeType(), true)
    }
}