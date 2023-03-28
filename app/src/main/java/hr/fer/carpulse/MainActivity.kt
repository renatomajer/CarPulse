package hr.fer.carpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import hr.fer.carpulse.navigation.Navigation
import hr.fer.carpulse.ui.theme.CarPulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarPulseTheme {
                // A surface container using the 'background' color from the theme
                Navigation()
            }
        }
    }
}