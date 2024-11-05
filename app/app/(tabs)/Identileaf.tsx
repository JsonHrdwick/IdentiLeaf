
import { useRouter } from 'expo-router';
import { StyleSheet, ActivityIndicator,TouchableOpacity, ImageBackground} from 'react-native';

import EditScreenInfo from '../../components/EditScreenInfo';
import { Text, View } from '../../components/Themed';
import {useState} from 'react';

import { Image } from 'react-native'



export default function TabTwoScreen() {

  const router = useRouter();
  
  return (
    
    <View style={styles.container}>

      <ImageBackground source={require('./greenbg.png')} resizeMode="contain" style={styles.background}>
      <Text style={styles.text}> </Text>
    </ImageBackground>
      <Text style={styles.title}></Text>

      


      <Text style={styles.title}>
        <Image source={require('./logo.png')}
        style={styles.logo} />
        </Text>
      
        <TouchableOpacity style={styles.button} onPress={() => {
    console.log('You tapped the button!');
    router.push('/registerScreen');
    }}>
        <Text style={styles.text}>Tap to get started!</Text>
      </TouchableOpacity>
      
        {/*<View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" /> */}
      
      
    </View>
  );
}

const styles = StyleSheet.create({

  background:{
    width: 1000,
    height: 1000,
    flex: 1,
    position:'absolute'
  },
  
  text:{
    fontSize: 20,
    fontFamily: 'Apple SD Gothic Neo'
  },


  logo:{
    width: 300, 
    height: 220,
  },

  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },

  title: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  separator: {
    marginVertical: 30,
    height: 1,
    width: '80%',
  },
  
 button: {
    display: "flex",
    alignItems: 'center',
    backgroundColor: '#86bc41',
    padding: 20,
    borderRadius: 100,
    
  },

});

