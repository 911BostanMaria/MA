import React, { useState, useContext, useEffect } from 'react';
import { View, Text, TextInput, Button, StyleSheet, TouchableOpacity } from 'react-native';
import { useDiaryPages } from '../DiaryPageContext'; // Update import
import { RootStackParamList } from '../../App';
import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';

type AddEditScreenRouteProp = RouteProp<RootStackParamList, 'AddEdit'>;
type AddEditScreenNavigationProp = StackNavigationProp<RootStackParamList, 'AddEdit'>;

interface AddEditScreenProps {
    route: AddEditScreenRouteProp;
    navigation: AddEditScreenNavigationProp;
}

function AddEditScreen({ route, navigation }: AddEditScreenProps) {
    const { addDiaryPage, updateDiaryPage } = useDiaryPages(); // Update function names
    const [title, setTitle] = useState<string>('');
    const [content, setContent] = useState<string>('');
    const [mood, setMood] = useState<string>('');
    const [location, setLocation] = useState<string>('');

    const diaryPage = route.params?.diaryPage;

    useEffect(() => {
        if (diaryPage) {
            setTitle(diaryPage.title);
            setContent(diaryPage.content);
            setMood(diaryPage.mood);
            setLocation(diaryPage.location);
        }
    }, [diaryPage]);

    const handleSave = () => {
        if (diaryPage) {
            updateDiaryPage(diaryPage.id, title, content, mood, location);
        } else {
            addDiaryPage(title, content, mood, location);
        }
        navigation.navigate('List');
    };

    return (
        <View style={styles.container}>
            <Text style={styles.label}>Diary Page Title:</Text>
            <TextInput
                style={styles.input}
                value={title}
                onChangeText={setTitle}
                placeholder="Enter diary page title"
            />
            <Text style={styles.label}>Content: </Text>
            <TextInput
                style={styles.input}
                value={content}
                onChangeText={setContent}
                placeholder="Enter diary content"
                multiline={true}
            />

            <Text style={styles.label}>Mood: </Text>
            <TextInput
                style={styles.input}
                value={mood}
                onChangeText={setMood}
                placeholder="Enter mood"
            />
            <Text style={styles.label}>Location: </Text>
            <TextInput
                style={styles.input}
                value={location}
                onChangeText={setLocation}
                placeholder="Enter location"
            />
            <TouchableOpacity style={styles.button} onPress={handleSave}>
                <Text style={styles.buttonText}>Save Diary Page</Text>
            </TouchableOpacity>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        justifyContent: 'flex-start',
        backgroundColor: '#FF6EC7', // Light gray background
    },
    label: {
        fontSize: 18,
        marginBottom: 8,
        color: '#333333', // Dark text for better readability
    },
    input: {
        borderWidth: 1,
        borderColor: '#cccccc', // Light gray border
        borderRadius: 8, // Rounded corners
        padding: 12,
        marginBottom: 20,
        fontSize: 16,
    },
    button: {
        backgroundColor: '##FF6EC7', // Blue background for the button
        padding: 15,
        borderRadius: 8, // Rounded corners for the button
        alignItems: 'center',
        marginTop: 20,
    },
    buttonText: {
        color: 'white',
        fontSize: 18,
        fontWeight: 'bold', // Bold text for the button
    },
});

export default AddEditScreen;
