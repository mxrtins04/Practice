const mockBookList = ()=>{
    return new Promise((resolve)=>{
        return resolve({
            books: [
                "The Great Gatsby",
                "To Kill a Mockingbird",
                "1984"
            ]
        })
    })
}

export default mockBookList;