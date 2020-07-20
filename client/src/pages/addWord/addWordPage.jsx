import React, {useState} from "react"
import "./addWordPage.css"


export function AddWordPage() {
    const [windowText, setWindowText] = useState("")
    const [translationText, setTranslationText] = useState("")
    const [addedWord, setAddedWord] = useState("")
    const [isError, setError] = useState(false)
    let timer = null

    const handleButtonToAddWord = () => {
        //TODO запрос на сохранение слова на сервере + добавить аккаунт id
        console.log(windowText)
        console.log(addedWord)
    }
    const handleAddedWordInput = (e) => {
        setAddedWord(e.target.value)
    }
    const handleWindowText = (e) => {
        setError(false)
        clearTimeout(timer)
        const text = e.target.value
        if (!/^[а-яА-ЯёЁ0-9\,\,\.\s]*$/.test(text)) {
            setError(true)
            return
        }
        timer = setTimeout(() => getTranslation(text), 1000)
    }

    const getTranslation = (phrase) => {
        //TODO запрос на google translate
        setWindowText(phrase)
        setTimeout(() => setTranslationText(Math.random()), 1000)
    }
    return (
        <div className="container-fluid wrapper">
            <div className={"row justify-content-center"}>
                <p className={"headerForGoogle"}>Google - Translater</p>
            </div>
            <div className="row justify-content-center">
                <div className={`col-4  `}>
                    <textarea className={`window ${isError ? "errorStatus" : ""}`} onChange={handleWindowText}/>
                </div>

                <div className="translation col-4">
                    {translationText}
                    <p>{isError}</p>
                </div>
            </div>

            <div className={"row justify-content-center mt-5"}>
                <div className={"col-3"}>
                    <input className={"inputAddWord"} value={addedWord} onChange={handleAddedWordInput}/>
                    <button className={"buttonAddWord"} onClick={handleButtonToAddWord}>
                        <i className="fa fa-plus-circle"/>
                    </button>
                </div>
            </div>
        </div>

    )
}