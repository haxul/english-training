import React from "react";
import {AddWordPage} from "./addWord/addWordPage"
import {TrainWaterfall} from "./trainWaterfall/trainWaterFall"
import {VocabularyPage} from "./vocabulary/vocabularyPage"
// pages of left menu
const pages = {
    "Add word" : <AddWordPage/>,
    "Training" : <TrainWaterfall/>,
    "Vocabulary" : <VocabularyPage/>
}

export default pages;