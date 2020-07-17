import React, {useContext} from "react"
import "./sidebar.css"
import {AppContext} from "../appContext"

export function Sidebar(props) {
    const {setActivePage} = useContext(AppContext)
    const menuItems = props.buttons ? props.buttons : [];
    const links = menuItems.map((item, index) => <a key={index} href="#" onClick={() => setActivePage(item)}
                                                    className="list-group-item list-group-item-action bg-light">{item}</a>)
    return (
        <div className="d-flex" id="wrapper">
            <div className="bg-light border-right" id="sidebar-wrapper">
                <div className="sidebar-heading">Menu</div>
                <div className="list-group list-group-flush">
                    {links}
                </div>
            </div>
        </div>
    )
}