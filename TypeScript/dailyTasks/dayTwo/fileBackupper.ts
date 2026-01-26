type BackupFile ={
    name : string;
    size : number;
    status : "processing" | "completed" | "failed";
}

const backupFiles: BackupFile[] = [
    {
        name: "file1.txt",
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

let startTime = Date.now();

function getDelayTime(file : BackupFile) {
    let ms = file.size * 100;

    return ms
}

const delay = (ms : number) => new Promise((resolve) => setTimeout(resolve, ms));

function uploadFile(file : BackupFile) {
    failure
}