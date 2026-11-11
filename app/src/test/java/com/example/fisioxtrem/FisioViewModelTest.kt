package com.example.fisioxtrem

import com.example.fisioxtrem.model.Cita
import com.example.fisioxtrem.viewmodel.FisioViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FisioViewModelTest {

    private val vm = FisioViewModel()

    @Test
    fun login_withDemoPassword_startsSession() {
        assertTrue(vm.login("anna@example.com", FisioViewModel.DEMO_PASSWORD))
        assertEquals("anna@example.com", vm.usuariLogejat?.email)
    }

    @Test
    fun login_withWrongPassword_doesNotStartSession() {
        assertFalse(vm.login("anna@example.com", "wrong"))
        assertNull(vm.usuariLogejat)
    }

    @Test
    fun login_withBlankEmail_doesNotStartSession() {
        assertFalse(vm.login("   ", FisioViewModel.DEMO_PASSWORD))
        assertNull(vm.usuariLogejat)
    }

    @Test
    fun logout_endsSession() {
        vm.login("anna@example.com", FisioViewModel.DEMO_PASSWORD)
        vm.logout()
        assertNull(vm.usuariLogejat)
    }

    @Test
    fun afegirCita_storesAppointment() {
        val centre = vm.centres.first()
        val professional = vm.professionals.first { it.centreId == centre.id }
        val cita = Cita(centre, professional, dataMillis = 0L, hora = 10, minut = 30)

        vm.afegirCita(cita)

        assertEquals(listOf(cita), vm.cites.toList())
    }

    @Test
    fun everyCentre_hasAtLeastOneProfessional() {
        vm.centres.forEach { centre ->
            assertTrue(
                "Centre ${centre.nom} has no professionals",
                vm.professionals.any { it.centreId == centre.id }
            )
        }
    }
}
