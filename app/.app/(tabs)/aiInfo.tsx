import { StyleSheet, TouchableOpacity, ScrollView, ImageBackground } from 'react-native';

import EditScreenInfo from '@/components/EditScreenInfo';
import { Text, View } from '@/components/Themed';
import { Image } from 'react-native'


export default function TabOneScreen() {
  return (
    <View style={styles.container}>

<ImageBackground source={require('./greenbg.png')} resizeMode="contain" style={styles.background}>

</ImageBackground>

      <Text style={styles.title}>Eastern Hemlock</Text>
      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" />
      <View style={{ height: 300 }}>
        <ScrollView zoomScale={0}>
          <Text style={styles.scrollview}>
          The Eastern Hemlock (Tsuga canadensis) is a coniferous tree native to eastern North America. Here’s some detailed information about it:

General Characteristics
Family: Pinaceae
Height: Typically grows to 40–70 feet (12–21 meters) tall, but can reach up to 100 feet (30 meters) in optimal conditions.
Trunk Diameter: Usually 1–2 feet (0.3–0.6 meters), but larger specimens can be found.
Leaves: The needles are flat, dark green, and typically 0.5–1 inch (1.3–2.5 cm) long, with a distinct white stripe on the underside.
Habitat
Range: Found in the eastern United States and parts of Canada, primarily in moist, well-drained soils. It thrives in cool, shady environments, often found in mixed coniferous and deciduous forests.
Climate: Prefers temperate climates with adequate moisture.
Ecology
Bark: The bark is thin, scaly, and grayish-brown, often becoming furrowed with age.
Cones: Produces small cones, typically 0.5–1 inch (1.3–2.5 cm) long, which hang down and mature in the fall. The seeds are dispersed by wind and animals.
Habitat for Wildlife: Provides shelter and food for various wildlife, including birds, squirrels, and insects.
Uses
Timber: Valued for its wood, which is lightweight and resistant to decay, often used for construction, furniture, and paper production.
Landscaping: Frequently used as an ornamental tree in gardens and parks due to its attractive foliage and graceful form.
Traditional Uses: Historically, Indigenous peoples used parts of the tree for medicinal purposes and to make tea from the inner bark, rich in vitamin C.
Conservation
Threats: The Eastern Hemlock faces threats from the invasive Hemlock Woolly Adelgid, an insect that feeds on the tree's sap, leading to decline and mortality. Conservation efforts are in place to manage these pests and preserve existing populations.
Summary
The Eastern Hemlock is a significant tree in North American forests, contributing to biodiversity, providing habitat for wildlife, and offering valuable resources. Its graceful appearance and ecological importance make it a treasured part of its native environment.
          </Text>
        </ScrollView></View>


      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" />

      <TouchableOpacity style={styles.button} onPress={() => {
        console.log('You tapped the button!');
      }}>

        <Text style={styles.text}>Add to identified trees</Text>
      </TouchableOpacity>

     

    </View>



  );
}

const styles = StyleSheet.create({

  scrollview: {
    fontSize: 20,
    backgroundColor: '#ffffff',
    color: '#000000',
  },

  background: {
    width: 1000,
    height: 1000,
    flex: 1,
    position: 'absolute'
  },

  text: {
    fontFamily: 'Apple SD Gothic Neo',
    fontSize: 20,
  },

  button: {
    backgroundColor: '#86bc41',
    padding: 10,
    borderRadius: 100
  },

  button2: {
    top: -44,
    right: -50,
    backgroundColor: '#86bc41',
    padding: 10,
    borderRadius: 1
  },

  image: {
    height: 250,
    width: 250,
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
});
