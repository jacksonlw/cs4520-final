package com.cs4520.brainflex

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cs4520.brainflex.ui.theme.BrainFlexTheme


@Composable
fun LogInScreen(
    viewModel: LogInViewModel,
    navHostController: NavHostController,
) {
    Surface(color = MaterialTheme.colors.secondary) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            val context = LocalContext.current
            var username by remember { mutableStateOf("") }
            Text(
                text = "LOGIN", modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary, style = MaterialTheme.typography.h4
            )
            Text(
                "Please sign in to continue", modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary, style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(40.dp))

            LoginField(
                value = username,
                onChange = { username = it },
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    viewModel.login(username)
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    navHostController.navigate(Screen.GAMESTART.name)
                },
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = MaterialTheme.colors.background,
                    backgroundColor = MaterialTheme.colors.background
                ),
                modifier = Modifier.align(Alignment.End),
                enabled = username.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text("Continue", color = MaterialTheme.colors.primary)
            }
        }
    }
}

@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "username",
) {

    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = Color.Gray
        )
    }
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.clearFocus() }
        ),
        label = { Text(label, color = Color.Gray) },
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
        )
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
                var username by remember { mutableStateOf("") }
                Text(
                    text = "LOGIN", modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary, style = MaterialTheme.typography.h3
                )
                Text(
                    "Please sign in to continue", modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary, style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(60.dp))
                LoginField(
                    value = username,
                    onChange = { username = it },
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        disabledBackgroundColor = MaterialTheme.colors.background,
                        backgroundColor = MaterialTheme.colors.background
                    ),
                    modifier = Modifier.align(Alignment.End),
                    enabled = username.isNotEmpty(),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text("Continue", color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}