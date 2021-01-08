pragma solidity ^0.5.6;
pragma experimental ABIEncoderV2;

contract FileList {

    uint public fileCount = 0;

    struct File {
        uint id;
        address owner;
        string name;
        string fileInfo;
        string dateInfo;
        string storage_address;
    }

    mapping(address => mapping(string => File[])) public files;
    mapping(address => string[]) public fileNames;

    event FileCreated(
        uint id,
        address owner,
        string name,
        string fileInfo,
        string dateInfo,
        string storage_address
    );


    function uploadFile(string memory _name, string memory _fileInfo, string memory _dateInfo, string memory _storage_address) public {
        address owner = msg.sender;
        fileCount ++;
        files[owner][_name].push(File(fileCount, owner, _name, _fileInfo, _dateInfo, _storage_address));
        fileNames[owner].push(_name);
        emit FileCreated(fileCount, owner, _name, _fileInfo, _dateInfo, _storage_address);
    }

    function getFileName(uint startIndex, uint endIndex)public view returns (string[] memory names){
        address owner = msg.sender;
        string[] memory allItems =  fileNames[owner];
        string[] memory result = new string[](endIndex-startIndex+1);
        uint arrayIndex = 0;
        for(uint i=startIndex; i<endIndex; i++){
            result[arrayIndex] = allItems[i];
            if(arrayIndex==0){
                arrayIndex++;
                result[arrayIndex] = allItems[i];
            }
            arrayIndex++;
        }
        return result;
    }

    function getUserFileCount() public view returns(uint){
        return fileNames[msg.sender].length;
    }
    function getFileChunkCount(string memory _filename) public view returns(uint){
        return files[msg.sender][_filename].length;
    }

    function isExists(uint _index) public view returns (bool) {
        /* if(files[_index].id != 0){
             return true;
         }*/
        return false;
    }

    function updateFileInfo(uint _index, string memory _fileInfo) public returns (bool success) {
        /* if(isExists(_index)){
             File storage file = files[_index];
             if(file.owner==msg.sender){
                 file.fileInfo = _fileInfo;
                 return true;
             }
         }*/
        return false;
    }

    function updateDateInfo(uint _index, string memory _dateInfo) public returns (bool success) {
        /*if(isExists(_index)){
            File storage file = files[_index];
            if(file.owner==msg.sender){
                file.dateInfo = _dateInfo;
                return true;
            }
        }*/
        return false;
    }


    function updateStorageAddress(uint _index, string memory _storage_address) public returns (bool success) {
        // if(isExists(_index)){
        //     File storage file = files[_index];
        //     if(file.owner==msg.sender){
        //         file.storage_address = _storage_address;
        //         return true;
        //     }
        // }
        return false;
    }


    // function getFileList() public view returns (uint[] memory id, address[] memory owner,  string[] memory name, string[] memory fileInfo, string[] memory dateInfo, string[] memory storage_address){
    //     uint[] memory idValue = new uint[](fileCount);
    //     address[] memory ownerValue = new address[](fileCount);
    //     string[] memory nameValue = new string[](fileCount);
    //     string[] memory fileInfoValue = new string[](fileCount);
    //     string[] memory dateInfoValue = new string[](fileCount);
    //     string[] memory storageAddressValue = new string[](fileCount);
    //     for (uint i = 0; i < fileCount ; i++) {
    //         uint index = i;
    //         index++;
    //         File storage tempFile = files[index];
    //         if(tempFile.owner==msg.sender){
    //             idValue[i] = tempFile.id;
    //             ownerValue[i] = tempFile.owner;
    //             nameValue[i] = tempFile.name;
    //             fileInfoValue[i] = tempFile.fileInfo;
    //             dateInfoValue[i] = tempFile.dateInfo;
    //             storageAddressValue[i] = tempFile.storage_address;
    //         }
    //     }
    //     return (idValue, ownerValue, nameValue, fileInfoValue, dateInfoValue, storageAddressValue);
    // }

    /* function getFileIds(uint startIndex, uint endIndex, uint pageSize) public view returns (uint[] memory ids){
         uint[] memory idsValue = new uint[](pageSize);
         uint arrayIndex = 0;
         File[]  memory allFiles = files[msg.sender];
         for (uint i = startIndex; i <=endIndex ; i++) {
             File memory tempFile = allFiles[i];
             idsValue[arrayIndex] = tempFile.id;
             arrayIndex++;
         }
         return (idsValue);
     }

      function getFileInfos(uint startIndex, uint endIndex, uint pageSize) public view returns (string[] memory infos){
         string[] memory infosValue = new string[](pageSize);
         uint arrayIndex = 0;
         File[]  memory allFiles = files[msg.sender];
         for (uint i = startIndex; i <=endIndex ; i++) {
             File memory tempFile = allFiles[i];
             infosValue[arrayIndex] = tempFile.fileInfo;
             arrayIndex++;
         }
         return (infosValue);
     }



     function getFileNames(uint startIndex, uint endIndex, uint pageSize) public view returns (string[] memory names){
         string[] memory idsValue = new string[](pageSize);
         uint arrayIndex = 0;
         File[]  memory allFiles = files[msg.sender];
         for (uint i = startIndex; i <=endIndex ; i++) {
             File memory tempFile = allFiles[i];
             idsValue[arrayIndex] = tempFile.name;
             arrayIndex++;
         }
         return (idsValue);
     }
 */
}
