package pmf.it.app.budgettracker.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pmf.it.app.budgettracker.R
import pmf.it.app.budgettracker.viewmodel.ProfileScreenViewModel

@Composable
@Preview(showBackground = true, backgroundColor = 0x000000)
fun ProfileScreen(viewModel: ProfileScreenViewModel = hiltViewModel()) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            ){
                Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription =  "Profile Picture")
            }
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(text = "Profile", style = MaterialTheme.typography.titleLarge)
                HorizontalDivider(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                )
                Text(text = "Username: ", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
                Text(text = "Name: ", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
                Text(text = "Email: ", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
            }
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.logout() },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)

                ) {
                    Text(text = "Logout")
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)

                ) {
                    Text(text = "Edit")
                }

            }
        }
    }
}