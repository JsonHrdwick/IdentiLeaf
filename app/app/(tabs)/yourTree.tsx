import { StyleSheet, TouchableOpacity, ImageBackground } from 'react-native';

import EditScreenInfo from '../../components/EditScreenInfo';
import { Text, View } from '../../components/Themed';
import { Image } from 'react-native'

import React from 'react';
import { SafeAreaView } from 'react-native';
import { Appbar } from 'react-native-paper';
import ImageSlider from './imageSlider'; // Import your ImageSlider component
import { useRouter } from 'expo-router';

export default function TabOneScreen() {

  const router = useRouter();
  return (
    <View style={styles.container}>

      <ImageBackground source={require('./greenbg.png')} resizeMode="contain" style={styles.background}>

      </ImageBackground>

      <Text style={styles.title}>Is this your tree?</Text>
      <Text style={styles.title2}>Eastern Hemlock</Text>
      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" />

      <ImageSlider />

      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" /> 

      <TouchableOpacity style={styles.button} onPress={() => {
        console.log('User answered: Yes');
        router.push('/aiInfo');
      }}>

        <Text style={styles.text}>Yes</Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.button2} onPress={() => {
        console.log('User answered: No');
      }}>
        <Text style={styles.text}>No</Text>
      </TouchableOpacity>



    </View>
  );
}

const styles = StyleSheet.create({
  text: {
    fontFamily: 'Apple SD Gothic Neo',
    fontSize: 20,
    alignSelf: "center",
  },

  button: {
    right: 50,
    width: 60,
    backgroundColor: '#86bc41',
    padding: 10,
    borderRadius: 100
  },

  button2: {
    top: -44,
    right: -50,
    width: 60,
    backgroundColor: '#86bc41',
    padding: 10,
    borderRadius: 100
  },

  image: {
    height: 250,
    width: 250,
    borderRadius: 10
  },
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    

    color: '#85bb41',
    fontSize: 20,
    fontWeight: 'bold',
    
  },

  title2: {
    

    color: '#ffffff',
    fontSize: 25,
    fontWeight: 'bold',
    
  },
  separator: {
    marginVertical: 10,
    height: 0,
    width: '100%',
  },

  background: {
    width: 1000,
    height: 1000,
    flex: 1,
    position: 'absolute'
  },
});
