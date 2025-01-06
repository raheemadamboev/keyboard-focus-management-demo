package xyz.teamgravity.keyboardfocusmanagementdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import xyz.teamgravity.keyboardfocusmanagementdemo.ui.theme.KeyboardFocusManagementDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KeyboardFocusManagementDemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .imePadding()
                    ) {
                        var name by rememberSaveable { mutableStateOf("") }
                        var email by rememberSaveable { mutableStateOf("") }
                        var password by rememberSaveable { mutableStateOf("") }

                        val requester = remember { FocusRequester() }
                        val manager = LocalFocusManager.current

                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.name)
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    manager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.9F)
                                .focusRequester(requester)
                        )
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.email)
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    manager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            modifier = Modifier.fillMaxWidth(0.9F)
                        )
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.password)
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    manager.clearFocus()
                                    Toast.makeText(this@MainActivity, getString(R.string.submitted), Toast.LENGTH_SHORT).show()
                                }
                            ),
                            modifier = Modifier.fillMaxWidth(0.9F)
                        )
                        Button(
                            onClick = {
                                requester.requestFocus()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.begin_filling_out_form)
                            )
                        }
                        Button(
                            onClick = {
                                manager.moveFocus(FocusDirection.Down)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.go_to_next_field)
                            )
                        }
                        Button(
                            onClick = {
                                manager.clearFocus()
                                Toast.makeText(this@MainActivity, getString(R.string.submitted), Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.submit)
                            )
                        }
                    }
                }
            }
        }
    }
}