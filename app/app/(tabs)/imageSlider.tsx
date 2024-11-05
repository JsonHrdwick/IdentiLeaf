import React from 'react';
import { ScrollView, Image, StyleSheet, View, Dimensions } from 'react-native';
import { Caption } from 'react-native-paper';

const images = [
  { id: 1, uri: 'https://www.coniferousforest.com/wp-content/uploads/2020/12/Types-of-Hemlock-Trees.jpg' },
  { id: 2, uri: 'https://ung.edu/environmental-leadership-center/_uploads/images/eco-lab/hemlock-new.jpg'  },
  { id: 3, uri: require('./tree3.jpg')  },
  // Add more image URIs as needed
];

const { width } = Dimensions.get('window');

const ImageSlider = () => {
  return (
    <View style={styles.container}>
      <ScrollView
        horizontal
        pagingEnabled
        showsHorizontalScrollIndicator={false}
      >
        {images.map((image) => (
          <View key={image.id} style={styles.imageContainer}>
            <Image source={image.uri} style={styles.image} />
            <Caption style={styles.caption}>Image {image.id}</Caption>
          </View>
        ))}
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 0.7,
  },
  imageContainer: {
    width,
    justifyContent: 'center',
    alignItems: 'center',
  },
  image: {
    borderRadius: 25,
    // width: '50%',
    width: 200,
    height: 300, // Adjust height as needed
    resizeMode: 'cover',
  },
  caption: {
    marginTop: 10,
  },
});

export default ImageSlider;