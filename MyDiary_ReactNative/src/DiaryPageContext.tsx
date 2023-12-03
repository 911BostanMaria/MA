import React, { createContext, useState, useContext, ReactNode } from 'react';

export interface DiaryPage {
    id: number;
    title: string;
    date: string; // Assuming the date is stored as a string for simplicity
    content: string;
    mood: string;
    location: string;
}

interface DiaryPagesContextType {
    diaryPages: DiaryPage[];
    addDiaryPage: (title: string, date: string, content: string, mood: string, location: string) => void;
    updateDiaryPage: (id: number, title: string, date: string, content: string, mood: string, location: string) => void;
    deleteDiaryPage: (id: number) => void;
}

const defaultContextValue: DiaryPagesContextType = {
    diaryPages: [],
    addDiaryPage: () => { },
    updateDiaryPage: () => { },
    deleteDiaryPage: () => { }
};

export const DiaryPagesContext = createContext<DiaryPagesContextType>(defaultContextValue);

interface DiaryPagesProviderProps {
    children: ReactNode;
}

export const DiaryPagesProvider: React.FC<DiaryPagesProviderProps> = ({ children }) => {
    const [diaryPages, setDiaryPages] = useState<DiaryPage[]>([]);

    const addDiaryPage = (title: string, date: string, content: string, mood: string, location: string) => {
        setDiaryPages([...diaryPages, { id: Date.now(), title, date, content, mood, location }]);
    };

    const updateDiaryPage = (id: number, title: string, date: string, content: string, mood: string, location: string) => {
        setDiaryPages(
            diaryPages.map((diaryPage) => (diaryPage.id === id ? { ...diaryPage, title, date, content, mood, location } : diaryPage))
        );
    };

    const deleteDiaryPage = (id: number) => {
        setDiaryPages(diaryPages.filter((diaryPage) => diaryPage.id !== id));
    };

    return (
        <DiaryPagesContext.Provider value={{ diaryPages, addDiaryPage, updateDiaryPage, deleteDiaryPage }}>
            {children}
        </DiaryPagesContext.Provider>
    );
};

export const useDiaryPages = () => {
    const context = useContext(DiaryPagesContext);
    if (!context) {
        throw new Error('useDiaryPages must be used within a DiaryPagesProvider');
    }
    return context;
};
