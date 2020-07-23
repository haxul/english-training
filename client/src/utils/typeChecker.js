function requireObject(typeList, object) {
    typeList.forEach(field => {
        if (object[field] === undefined) throw "DataTypeException"
    })
}

function requireNumber(int) {
    if (typeof int !== "number") throw "DataTypeException"
}

function requireString(string) {
    if (typeof string !== "string") throw "DataTypeException"
}
export default {
    requireNumber,
    requireString,
    requireObject
}