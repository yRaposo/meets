import { NavigationContainer, useNavigation } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, TextInput, TouchableHighlight, View } from 'react-native';
import LoginScreen from './src/screens/public/LoginScreen';

export default function App() {

  return (
    <NavigationContainer>
      <RootStack />
    </NavigationContainer>
  );
}

const Stack = createNativeStackNavigator();

function RootStack() {
  return (
    <Stack.Navigator
      initialRouteName='Login'
      screenOptions={{
        title: 'meets',
        headerStyle: { backgroundColor: '#9C2222' },
        headerTintColor: '#fff',
        headerTitleStyle: {
          fontWeight: 'bold',
          fontSize: 30
        },
        headerTitleAlign: 'center'
      }}
    >
      <Stack.Screen name="Login" component={LoginScreen} />
    </Stack.Navigator>
  )
}