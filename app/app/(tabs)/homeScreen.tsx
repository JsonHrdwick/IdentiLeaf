import React from 'react';
import { StyleSheet, View, ImageBackground} from 'react-native';
import { Avatar, Button, Card, Title, Paragraph } from 'react-native-paper';
import { useRouter } from 'expo-router';

const HomeScreen = () => {

  const router = useRouter();

  const navigateToQuestions = () => {
    router.push('/questions');
  };

  const navigateToTrees = () => {
    router.push('/identifiedTrees');
  };

  const handleButtonPress = (buttonName) => {
    console.log(`${buttonName} button pressed`);
  
  };

  
  return (
    <View style={styles.container}>
<ImageBackground source={require('./greenbg.png')} resizeMode="contain" style={styles.background}>

</ImageBackground>

      <Card style={styles.card}>
        <Card.Content style={styles.cardContent}>
          <Avatar.Image size={125} source={require('./logo.png')} style={styles.avatar} />
          <Title style={styles.title}>Welcome Back!</Title>
          <Paragraph style={styles.paragraph}>What are we doing today?</Paragraph>
          <Button
            mode="contained"
            onPress={navigateToQuestions}
            
            style={styles.button}
            buttonColor="#86bc41"
          >
            Identify a tree!
          </Button>
          <Button
            mode="contained"
            onPress={navigateToTrees}
            style={styles.button}
            buttonColor="#86bc41" 
          >
            View identified trees
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
  card: {
    padding: 16,
  },
  cardContent: {
    alignItems: 'center',
  },
  avatar: {
    backgroundColor: '#ffffff', 
  },
  title: {
    marginVertical: 16,
    textAlign: 'center',
  },
  paragraph: {
    textAlign: 'center',
    marginBottom: 16,
  },
  button: {
    marginVertical: 8,
    width: '100%',
  },
});

export default HomeScreen;

