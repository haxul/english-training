import React, {useState, useEffect} from "react"
import {baseUrl, createAuthHeaders, checkAuth} from "../../utils/connection"
import typeChecker from "../../utils/typeChecker"
import "./trainWaterFall.css"

async function getAllWord() {
    const token = checkAuth()
    const response = await fetch(baseUrl + "/words?page=0", {
        headers: createAuthHeaders(token),
        method: "GET"
    })
    if (response.status === 200) return await response.json()
    return []
}

export function TrainWaterfall() {
    const [words, setWords] = useState([])
    useEffect(() => {
        (async () => setWords(await getAllWord()))()
    }, []);
    const list = words.map((word, index) => (
        <div key={index} className={"row justify-content-center"}>
            <div className={"col-4 word mb-2"}>{word.value}</div>
        </div>
    ))

    return (
        <div className={"container"}>
            <div className={"options "}>
                <div className={"row justify-content-center"}>
                    <div className={"col-4 mb-5 guess mt-3"}> Guess</div>
                </div>
                {list}
            </div>
        </div>
    )
}