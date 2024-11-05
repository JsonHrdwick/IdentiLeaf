import React, { useState } from 'react';
import { StyleSheet, View, ImageBackground } from 'react-native';
import { TextInput, Button, Text, Card } from 'react-native-paper';

const AuthScreen = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = () => {
    // Handle login or registration logic here
    console.log(isLogin ? 'Logging in' : 'Registering', { email, password });
  };

  return (
    <View style={styles.container}>

<ImageBackground source={require('./greenbg.png')} resizeMode="contain" style={styles.background}>

</ImageBackground>

      <Card>
        <Card.Content>
          <Text style={styles.title}>{isLogin ? 'Login' : 'Register'}</Text>
          <TextInput
            label="Email"
            value={email}
            onChangeText={setEmail}
            style={styles.input}
            mode="outlined"
            autoCapitalize="none"
            keyboardType="email-address"
            textContentType="emailAddress"
          />
          <TextInput
            label="Password"
            value={password}
            onChangeText={setPassword}
            style={styles.input}
            mode="outlined"
            secureTextEntry
            textContentType="password"
          />
          <Button buttonColor="#86bc41" mode="contained" onPress={handleSubmit} style={styles.button}>
            {isLogin ? 'Login' : 'Register'}
          </Button>
          <Button textColor="#86bc41"onPress={() => setIsLogin(!isLogin)} style={styles.switchButton}>
            
            Switch to {isLogin ? 'Register' : 'Login'}
          </Button>
        </Card.Content>
      </Card>
    </View>
  );
};

const styles = StyleSheet.create({
  background: {
    left: -305,
    width: 1000,
    height: 1000,
    flex: 1,
    position: 'absolute'
  },

  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 16,
  },
  title: {
    fontSize: 24,
    marginBottom: 16,
    textAlign: 'center',
  },
  input: {
    marginBottom: 16,
  },
  button: {
    marginBottom: 8,
  },
  switchButton: {
    marginTop: 8,
  },
});

export default AuthScreen;
