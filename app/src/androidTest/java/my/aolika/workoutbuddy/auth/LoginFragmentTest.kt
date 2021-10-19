package my.aolika.workoutbuddy.auth

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import my.aolika.workoutbuddy.MainActivity
import my.aolika.workoutbuddy.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @Test
    fun clickForgotPasswordButton_navigateToResetPasswordFragment() {
        // create a TestNavHostController
  //      val navController = TestNavHostController(
    //        ApplicationProvider.getApplicationContext()
      //  )

        val mockNavController = mock(NavController::class.java)

        //create a graphical FragmentScenario for the LoginFragment
        val loginScenario = launchFragmentInContainer<LoginFragment>()

        loginScenario.onFragment { fragment ->
        //set a graph on the TestNavHostController
        //    navController.setGraph(R.navigation.nav_graph)

            //make the navController available via the findController() APIs
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.btn_forgot_password)).perform(click())
     //   assertThat(navController.currentDestination?.id).isEqualTo(R.id.resetPasswordFragment)
        verify(mockNavController).navigate(R.id.resetPasswordFragment)
    }
}

