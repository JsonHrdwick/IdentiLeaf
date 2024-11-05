import { StyleSheet, TouchableOpacity, ImageBackground } from 'react-native';

import EditScreenInfo from '@/components/EditScreenInfo';
import { Text, View } from '@/components/Themed';
import { Image } from 'react-native'


export default function TabOneScreen() {
  return (
    <View style={styles.container}>

      <ImageBackground source={require('./greenbg.png')} resizeMode="contain" style={styles.background}>

      </ImageBackground>

      <Text style={styles.title}>Example question?</Text>
      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" />

      <Image source={require('./leaf.jpg')}
        style={styles.image} />

      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" /> 

      <TouchableOpacity style={styles.button} onPress={() => {
        console.log('You tapped the button!');
      }}>

        <Text style={styles.text}>Yes</Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.button2} onPress={() => {
        console.log('You tapped the button!');
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
    borderRadius: 20
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
