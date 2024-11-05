import React from 'react';
import { StyleSheet, View, ImageBackground } from 'react-native';
import { DataTable, Text } from 'react-native-paper';

const TreeTable = () => {
  // Sample data
  const treeData = [
    { name: 'Eastern Hemlock', dateIdentified: '2023-01-15' },
    { name: 'Red Maple', dateIdentified: '2023-02-10' },
    { name: 'White Oak', dateIdentified: '2023-03-05' },
    { name: 'Sugar Maple', dateIdentified: '2023-04-22' },
  ];

  return (
    <View style={styles.container}>
<ImageBackground source={require('./greenbg.png')} resizeMode="contain" style={styles.background}>

</ImageBackground>

      <DataTable>
        <DataTable.Header>
          <DataTable.Title>
            <Text style={styles.headerText}>Tree Name</Text>
          </DataTable.Title>
          <DataTable.Title>
            <Text style={styles.headerText}>Date Identified</Text>
          </DataTable.Title>
        </DataTable.Header>

        {treeData.map((tree, index) => (
          <DataTable.Row key={index}>
            <DataTable.Cell>
              <Text style={styles.cellText}>{tree.name}</Text>
            </DataTable.Cell>
            <DataTable.Cell>
              <Text style={styles.cellText}>{tree.dateIdentified}</Text>
            </DataTable.Cell>
          </DataTable.Row>
        ))}

        <DataTable.Pagination
          page={1}
          numberOfPages={1}
          onPageChange={(page) => {
            console.log(page);
          }}
          label="1-4 of 4"
        />
      </DataTable>
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
    padding: 16,
  },
  headerText: {
    color: '#000000', // Header text color
  },
  cellText: {
    color: '#000000', // Cell text color
  },
});

export default TreeTable;