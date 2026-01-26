type BackupFile ={
    name : string;
    size : number;
    status : "processing" | "completed" | "failed";
}


const backupFiles: BackupFile[] = [
    {
        name: "jungleJustice.txt",
        size: 10,
        status: "processing"
    },
    {
        name: "file2.txt",
        size: 5,
        status: "processing"
    },
    {
        name: "file3.txt",
        size: 50,
        status: "processing"
    }
]

let file1 : BackupFile = backupFiles[0];


function getDelayTime(file : BackupFile) {
    let ms = file.size * 100;

    return ms
}

const delay = (ms : number) => new Promise((resolve) => setTimeout(resolve, ms));

async function uploadFile(file : BackupFile) {
    let startTime = Date.now();
    let successOrFailure = Math.random();
    let delayTime = getDelayTime(file);
    console.log("Backing up your file...");
    await delay(delayTime);
    if(successOrFailure >= 0.2) {
        file.status = "completed";
        console.log(`${file.name} has been backed up succesfuly`)
    } else {
        file.status = "failed";
        console.log(`failed`)
    }
    let endTime = Date.now();
    console.log(`Time taken: ${endTime - startTime}`);
}

uploadFile(file1);