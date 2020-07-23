import React, {useState, useEffect} from "react";
import "./vocabularyPage.css"
import {baseUrl, createAuthHeaders, checkAuth} from "../../utils/connection"
import typeChecker from "../../utils/typeChecker"
//TODO сделать пагинацию
async function getVocabulary(page) {
    const token = checkAuth()
    typeChecker.requireNumber(page)
    const url = `${baseUrl}/words?page=${page}`
    const response = await fetch(url, {
        method: "GET",
        headers: createAuthHeaders(token)
    })
    if (response.status === 200) {
        return await response.json()
    }
    return null
}

async function updateWordServer(word) {
    const token = checkAuth()
    typeChecker.requireObject(["id", "value", "translation"], word)
    const url = `${baseUrl}/words`
    const body = JSON.stringify({
        wordId: word.id,
        value: word.value,
        translation: word.translation
    })
    fetch(url, {
        method: "PUT",
        headers:createAuthHeaders(token),
        body
    })
}

export function VocabularyPage() {
    checkAuth()
    const [words, setWords] = useState([])
    useEffect(() => {
        (async () => setWords(await getVocabulary(0)))()
    }, []);

    if (words === null) return "error, words is null"

    const handleWordValue = (event, id) => {
        const text = event.target.value
        const newWords = words.map(word => (word.id === id) ? {
            "id": word.id,
            "value": text,
            "translation": word.translation
        } : word)
        setWords(newWords)
    }
    const handleWordTranslation = (event, id) => {
        const text = event.target.value
        const newWords = words.map(word => (word.id === id) ? {
            "id": word.id,
            "value": word.value,
            "translation": text
        } : word)
        setWords(newWords)
    }

    const updateWordById = (id) => {
        words.forEach(word => {
            if (word.id === id) updateWordServer(word)
        })
    }

    const list = words.map((word, index) => (
        <div key={index} className={"row justify-content-center vocabulary"}>
            <div className={"col-8"}>
                <input value={word.value}
                       onChange={(e) => handleWordValue(e, word.id)}
                       onBlur={() => updateWordById(word.id)}
                />
                <input value={word.translation}
                       onBlur={() => updateWordById(word.id)}
                       onChange={(e) => handleWordTranslation(e, word.id)}/>
            </div>
        </div>
    ))
    return (
        <div className={"container"}>
            <div className={"row justify-content-center"}>
                <p className={"headerForGoogle"}>Vocabulary</p>
            </div>
            {list}
        </div>
    )
}