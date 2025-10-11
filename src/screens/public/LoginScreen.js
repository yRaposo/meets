
import { StatusBar } from "expo-status-bar";
import { useState } from "react";
import { SafeAreaViewBase, StyleSheet, Text, TextInput, TouchableHighlight, View } from "react-native";
import MaterialIcons from '@expo/vector-icons/MaterialIcons';

export default function LoginScreen({ navigation }) {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('')
    const [showPassword, setShowPassword] = useState(true);



    return (
        <View style={styles.mainContainer}>
            <StatusBar style="light" />

            <View style={styles.formContainer}>
                <Text style={styles.title}>
                    Acesse o Seu Login
                </Text>

                <View style={styles.inputContainer}>
                    <TextInput
                        placeholder="E-mail"
                        keyboardType="email-address"
                        autoCapitalize="none"
                        autoCorrect={false}
                        value={email}
                        onChangeText={setEmail}
                        style={styles.input}
                    />
                    {email && (
                        <TouchableHighlight onPress={() => setEmail('')} style={{borderRadius: 100}}>
                            <MaterialIcons name="cancel" size={24} color="#979797ff"/>
                        </TouchableHighlight>
                    )}
                </View>

                <View style={styles.inputContainer}>
                    <TextInput
                        placeholder="Senha"
                        secureTextEntry={showPassword}
                        value={password}
                        onChangeText={setPassword}
                        style={styles.input}
                    />
                    {password && (
                        <TouchableHighlight onPress={() => setShowPassword(!showPassword)} style={{borderRadius: 100}}>
                            <MaterialIcons name={showPassword ? "visibility" : "visibility-off"} size={24} color="#979797ff" />
                        </TouchableHighlight>
                    )}
                </View>

                <View style={styles.container}>
                    <TouchableHighlight
                        onPress={() => console.log('Entrar')}
                        style={styles.btnPrimary}
                    >
                        <Text style={styles.txtBtnPrimary}>Entrar</Text>
                    </TouchableHighlight>
                    <TouchableHighlight
                        onPress={() => console.log('Criar Conta')}
                        style={styles.btnSecondary}
                    >
                        <Text style={styles.txtBtnSecondary}>Criar Conta</Text>
                    </TouchableHighlight>
                    <TouchableHighlight
                        onPress={() => console.log('Recuperar Senha')}
                        style={styles.btnLink}
                    >
                        <Text style={styles.txtBtnLink}>Recuperar Senha</Text>
                    </TouchableHighlight>

                </View>

            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },

    formContainer: {
        width: '80%',
        alignItems: 'center',
        gap: 20
    },

    title: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#9C2222',
        marginBottom: 20
    },

    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        width: '100%',
        paddingHorizontal: 20,
        borderRadius: 100,
        borderWidth: 1,
        borderColor: '#979797ff',
    },

    input: {
        height: 50,
        width: '85%',
    },

    container: {
        width: '100%',
        gap: 10,
        marginTop: 10,
        alignItems: 'center'
    },

    btnPrimary: {
        backgroundColor: '#9C2222',
        borderRadius: 100,
        paddingVertical: 12,
        paddingHorizontal: 20,
        width: '100%',
    },

    txtBtnPrimary: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
        textAlign: 'center'
    },
    btnSecondary: {
        borderColor: '#9C2222',
        borderWidth: 1,
        borderRadius: 100,
        paddingVertical: 12,
        paddingHorizontal: 20,
        width: '100%',
    },
    txtBtnSecondary: {
        color: '#9C2222',
        fontSize: 16,
        fontWeight: 'bold',
        textAlign: 'center'
    },
    btnLink: {
        paddingVertical: 12,
        paddingHorizontal: 20,
        width: '100%',
    },
    txtBtnLink: {
        color: '#9C2222',
        fontSize: 16,
        fontWeight: 'bold',
        textAlign: 'center'
    },
})