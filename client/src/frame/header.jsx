import React from "react"
import "./header.css"

export function Header(props) {
    return (

        <div className="header">
            <div className="container-fluid">
                <div className="row align-items-center username justify-content-end">
                    <div className="col-2">
                        account : {props.username}
                    </div>
                </div>
            </div>
        </div>
    )
}
