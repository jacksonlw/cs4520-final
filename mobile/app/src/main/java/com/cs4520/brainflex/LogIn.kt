package com.cs4520.brainflex

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cs4520.brainflex.ui.theme.BrainFlexTheme
import androidx.compose.ui.unit.sp


@Composable
fun LogInScreen (
//    viewModel: LogInViewModel,
    navHostController: NavHostController,
) {
    Surface ( color = MaterialTheme.colors.secondary ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            val context = LocalContext.current
            var credentials by remember { mutableStateOf(User()) }
            Text(text="LOGIN", modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary, style = MaterialTheme.typography.h3)
            Text("Please sign in to continue",  modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary, style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(60.dp))
            LoginField(
                value = credentials.username,
                onChange = { credentials = credentials.copy(username = it) },
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordField(
                value = credentials.pwd,
                onChange = { credentials = credentials.copy(pwd = it) },
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (checkCredentials(credentials, context)) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        navHostController.navigate(Screen.GAMESTART.name)
                    }
                },
                colors = ButtonDefaults.buttonColors(disabledBackgroundColor= MaterialTheme.colors.background,
                    backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier.align(Alignment.End),
                enabled = credentials.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    label: String = "username",
) {
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
        focusedIndicatorColor =  Color.Transparent, //hide the indicator
        unfocusedIndicatorColor =  MaterialTheme.colors.primary)
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    label: String = "password",
) {
    TextField(
        value = value,
        onValueChange = onChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor =  Color.Transparent, //hide the indicator
            unfocusedIndicatorColor =  MaterialTheme.colors.primary)
    )
}


@Preview(showBackground = true)
@Composable
fun LogInPreview() {
    BrainFlexTheme {
        Surface(
            color = MaterialTheme.colors.secondary
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                var credentials by remember { mutableStateOf(User()) }
                Text(text="LOGIN", modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary, style = MaterialTheme.typography.h3)
                Text("Please sign in to continue",  modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.height(60.dp))
                LoginField(
                    value = credentials.username,
                    onChange = { credentials = credentials.copy(username = it) },
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordField(
                    value = credentials.pwd,
                    onChange = { credentials = credentials.copy(pwd = it) },
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(disabledBackgroundColor= MaterialTheme.colors.background,
                        backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier.align(Alignment.End),
                    enabled = credentials.isNotEmpty(),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text("Continue", color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}


fun checkCredentials(userInfo: User, context: Context): Boolean {
    return true
}