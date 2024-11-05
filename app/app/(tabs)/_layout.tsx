import React from 'react';
import FontAwesome from '@expo/vector-icons/FontAwesome';
import { Link, Tabs } from 'expo-router';
import { Pressable } from 'react-native';

import Colors from '../../constants/Colors';
import { useColorScheme } from '../../components/useColorScheme';
import { useClientOnlyValue } from '../../components/useClientOnlyValue';



// You can explore the built-in icon families and icons on the web at https://icons.expo.fyi/
function TabBarIcon(props: {
  name: React.ComponentProps<typeof FontAwesome>['name'];
  color: string;
}) {
  return <FontAwesome size={28} style={{ marginBottom: -3 }} {...props} />;
}

export default function TabLayout() {
  const colorScheme = useColorScheme();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: Colors[colorScheme ?? 'light'].tint,
        // Disable the static render of the header on web
        // to prevent a hydration error in React Navigation v6.
        headerShown: useClientOnlyValue(false, true),
      }}>
<Tabs.Screen
        name="Identileaf"
        options={{
          title: 'IdentiLeaf',
          tabBarIcon: ({ color }) => <TabBarIcon name="tree" color={color} />,
          headerRight: () => (
            <Link href="/modal" asChild>
              <Pressable>
                {({ pressed }) => (
                  <FontAwesome
                    name="info-circle"
                    size={25}
                    color={Colors[colorScheme ?? 'light'].text}
                    style={{ marginRight: 15, opacity: pressed ? 0.5 : 1 }}
                  />
                )}
              </Pressable>
            </Link>
          ),
        }}
      />

<Tabs.Screen
        name="registerScreen"
        options={{
          title: 'Register Screen',
          tabBarIcon: ({ color }) => <TabBarIcon name="user" color={color} />,
        }}
      />

<Tabs.Screen
        name="homeScreen"
        options={{
          title: 'Home Screen',
          tabBarIcon: ({ color }) => <TabBarIcon name="home" color={color} />,
        }}
      />

      
      <Tabs.Screen
        name="questions"
        options={{
          title: 'Questions',
          tabBarIcon: ({ color }) => <TabBarIcon name="question" color={color} />,
        }}
      />

<Tabs.Screen
        name="yourTree"
        options={{
          title: 'Tree',
          tabBarIcon: ({ color }) => <TabBarIcon name="tree" color={color} />,
        }}
      />

<Tabs.Screen
        name="aiInfo"
        options={{
          title: 'AI Info',
          tabBarIcon: ({ color }) => <TabBarIcon name="cloud" color={color} />,
        }}
      />

<Tabs.Screen
        name="imageSlider"
        options={{
          href: null,
        }}
      />

    </Tabs>

    
  );
}
