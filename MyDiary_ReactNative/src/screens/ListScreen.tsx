import React from "react";
import { Button, View, FlatList, Text, TouchableOpacity, Alert, Platform, Image } from "react-native";
import { RootStackParamList } from "../../App";
import { StackNavigationProp } from '@react-navigation/stack'
import { useDiaryPages } from "../DiaryPageContext"; // Update import
import { StyleSheet } from "react-native";

type ListScreenNavigationProp = StackNavigationProp<
    RootStackParamList,
    'List'
>;

type Props = {
    navigation: ListScreenNavigationProp;
};

function ListScreen({ navigation }: Props) {
    const { diaryPages, deleteDiaryPage } = useDiaryPages(); // Update function and state names

    const confirmDelete = (id: number) => {
        if (Platform.OS === "android" || Platform.OS === "ios") {
            Alert.alert(
                "Delete diary page",
                "Are you sure you want to delete this diary page?",
                [
                    {
                        text: 'Cancel',
                        style: 'cancel',
                    },
                    {
                        text: 'Ok',
                        onPress: () => deleteDiaryPage(id),
                    },
                ]
            );
        } else if (Platform.OS === "web") {
            const confirm = window.confirm("Are you sure you want to delete this diary page?");
            if (confirm) {
                deleteDiaryPage(id);
            }
        }
    };

    return (
        <View style={styles.container}>
            <FlatList
                data={diaryPages}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => (
                    <View style={styles.item}>
                        <View style={styles.infoContainer}>
                            <Text style={styles.title}>{item.title}</Text>
                            <Text style={styles.price}>{item.content}</Text>
                            <Text style={styles.price}>{item.location}</Text>
                        </View>
                        <View style={styles.buttonContainer}>
                            <TouchableOpacity
                                style={styles.button}
                                onPress={() => navigation.navigate('AddEdit', { diaryPage: item })}>
                                <Text style={styles.buttonText}>Edit</Text>
                            </TouchableOpacity>
                            <TouchableOpacity
                                style={styles.button}
                                onPress={() => confirmDelete(item.id)}>
                                <Text style={styles.buttonText}>Delete</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                )}
            />
            <TouchableOpacity
                style={styles.addButton}
                onPress={() => navigation.navigate('AddEdit', {})}
            >
                <Text style={styles.addButtonText}>+</Text>
            </TouchableOpacity>
        </View>
    );
}

const styles = StyleSheet.create({
    // ... (rest of the styles remain unchanged)
});

export default ListScreen;
