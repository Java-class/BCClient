package ir.javaclass.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class FileList extends Contract {
    private static final String BINARY = "60806040526000805534801561001457600080fd5b50610eac806100246000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80639aed4a2c116100715780639aed4a2c146100cc5780639d36788e14610124578063b07a735b14610137578063b2a4200d14610157578063f0286d4614610177578063fe8712e71461018c576100a9565b806343953519146100ae5780634429c79e146100cc57806350bdd534146100cc57806371a58452146100ec578063963e9ba4146100ff575b600080fd5b6100b6610194565b6040516100c39190610cfd565b60405180910390f35b6100df6100da366004610b51565b61019a565b6040516100c39190610cde565b6100b66100fa366004610a36565b6101a3565b61011261010d366004610997565b6101d7565b6040516100c396959493929190610d0b565b6100df610132366004610b33565b61047b565b61014a6101453660046109fc565b610481565b6040516100c39190610cec565b61016a610165366004610b99565b610535565b6040516100c39190610ccd565b61018a610185366004610a73565b6106dc565b005b6100b6610881565b60005481565b60005b92915050565b3360009081526001602052604080822090516101c0908490610cc1565b908152604051908190036020019020549050919050565b6001602090815260008481526040902083518085018301805192815290830192850192909220915280548290811061020b57fe5b6000918252602091829020600691909102018054600180830154600280850180546040805161010096831615969096026000190190911692909204601f81018890048802850188019092528184529398506001600160a01b039091169650929450929091908301828280156102c15780601f10610296576101008083540402835291602001916102c1565b820191906000526020600020905b8154815290600101906020018083116102a457829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156103515780601f1061032657610100808354040283529160200191610351565b820191906000526020600020905b81548152906001019060200180831161033457829003601f168201915b5050505060048301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156103e15780601f106103b6576101008083540402835291602001916103e1565b820191906000526020600020905b8154815290600101906020018083116103c457829003601f168201915b5050505060058301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156104715780601f1061044657610100808354040283529160200191610471565b820191906000526020600020905b81548152906001019060200180831161045457829003601f168201915b5050505050905086565b50600090565b6002602052816000526040600020818154811061049a57fe5b600091825260209182902001805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815294509092509083018282801561052d5780601f106105025761010080835404028352916020019161052d565b820191906000526020600020905b81548152906001019060200180831161051057829003601f168201915b505050505081565b336000818152600260209081526040808320805482518185028101850190935280835260609594869484015b8282101561060c5760008481526020908190208301805460408051601f60026000196101006001871615020190941693909304928301859004850281018501909152818152928301828280156105f85780601f106105cd576101008083540402835291602001916105f8565b820191906000526020600020905b8154815290600101906020018083116105db57829003601f168201915b505050505081526020019060010190610561565b505050509050606085850360010160405190808252806020026020018201604052801561064d57816020015b60608152602001906001900390816106385790505b5090506000865b868110156106d05783818151811061066857fe5b602002602001015183838151811061067c57fe5b602002602001018190525081600014156106c45781806001019250508381815181106106a457fe5b60200260200101518383815181106106b857fe5b60200260200101819052505b60019182019101610654565b50909695505050505050565b6000805460019081018255338083526020919091526040918290209151909190610707908790610cc1565b908152604080516020928190038301812060c0820183526000805483526001600160a01b038681168685019081529484018b8152606085018b9052608085018a905260a0850189905283546001808201808755958552938890208651600690920201908155955192860180546001600160a01b0319169390921692909217905551805191949293926107a192600285019290910190610895565b50606082015180516107bd916003840191602090910190610895565b50608082015180516107d9916004840191602090910190610895565b5060a082015180516107f5916005840191602090910190610895565b5050506001600160a01b03821660009081526002602090815260408220805460018101808355918452928290208951919450610835930191890190610895565b50507fd1317857f68e28b2dca512c58b1c90b58cac97d4726832a31e60b039e3aabcbf600054828787878760405161087296959493929190610d0b565b60405180910390a15050505050565b336000908152600260205260409020545b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106108d657805160ff1916838001178555610903565b82800160010185558215610903579182015b828111156109035782518255916020019190600101906108e8565b5061090f929150610913565b5090565b61089291905b8082111561090f5760008155600101610919565b803561019d81610e49565b600082601f83011261094957600080fd5b813561095c61095782610da7565b610d80565b9150808252602083016020830185838301111561097857600080fd5b610983838284610e03565b50505092915050565b803561019d81610e60565b6000806000606084860312156109ac57600080fd5b60006109b8868661092d565b935050602084013567ffffffffffffffff8111156109d557600080fd5b6109e186828701610938565b92505060406109f28682870161098c565b9150509250925092565b60008060408385031215610a0f57600080fd5b6000610a1b858561092d565b9250506020610a2c8582860161098c565b9150509250929050565b600060208284031215610a4857600080fd5b813567ffffffffffffffff811115610a5f57600080fd5b610a6b84828501610938565b949350505050565b60008060008060808587031215610a8957600080fd5b843567ffffffffffffffff811115610aa057600080fd5b610aac87828801610938565b945050602085013567ffffffffffffffff811115610ac957600080fd5b610ad587828801610938565b935050604085013567ffffffffffffffff811115610af257600080fd5b610afe87828801610938565b925050606085013567ffffffffffffffff811115610b1b57600080fd5b610b2787828801610938565b91505092959194509250565b600060208284031215610b4557600080fd5b6000610a6b848461098c565b60008060408385031215610b6457600080fd5b6000610b70858561098c565b925050602083013567ffffffffffffffff811115610b8d57600080fd5b610a2c85828601610938565b60008060408385031215610bac57600080fd5b6000610a1b858561098c565b6000610bc48383610c51565b9392505050565b610bd481610de7565b82525050565b6000610be582610dd5565b610bef8185610dd9565b935083602082028501610c0185610dcf565b8060005b85811015610c3b5784840389528151610c1e8582610bb8565b9450610c2983610dcf565b60209a909a0199925050600101610c05565b5091979650505050505050565b610bd481610df2565b6000610c5c82610dd5565b610c668185610dd9565b9350610c76818560208601610e0f565b610c7f81610e3f565b9093019392505050565b6000610c9482610dd5565b610c9e8185610de2565b9350610cae818560208601610e0f565b9290920192915050565b610bd481610892565b6000610bc48284610c89565b60208082528101610bc48184610bda565b6020810161019d8284610c48565b60208082528101610bc48184610c51565b6020810161019d8284610cb8565b60c08101610d198289610cb8565b610d266020830188610bcb565b8181036040830152610d388187610c51565b90508181036060830152610d4c8186610c51565b90508181036080830152610d608185610c51565b905081810360a0830152610d748184610c51565b98975050505050505050565b60405181810167ffffffffffffffff81118282101715610d9f57600080fd5b604052919050565b600067ffffffffffffffff821115610dbe57600080fd5b506020601f91909101601f19160190565b60200190565b5190565b90815260200190565b919050565b600061019d82610df7565b151590565b6001600160a01b031690565b82818337506000910152565b60005b83811015610e2a578181015183820152602001610e12565b83811115610e39576000848401525b50505050565b601f01601f191690565b610e5281610de7565b8114610e5d57600080fd5b50565b610e528161089256fea365627a7a72315820bfbdf4e901efc0509be49021ac1e91baed4836c64af92fd53d9e3b4e762cc33d6c6578706572696d656e74616cf564736f6c634300050c0040";

    public static final String FUNC_FILECOUNT = "fileCount";

    public static final String FUNC_FILENAMES = "fileNames";

    public static final String FUNC_FILES = "files";

    public static final String FUNC_GETFILECHUNKCOUNT = "getFileChunkCount";

    public static final String FUNC_GETFILENAME = "getFileName";

    public static final String FUNC_GETUSERFILECOUNT = "getUserFileCount";

    public static final String FUNC_ISEXISTS = "isExists";

    public static final String FUNC_UPDATEDATEINFO = "updateDateInfo";

    public static final String FUNC_UPDATEFILEINFO = "updateFileInfo";

    public static final String FUNC_UPDATESTORAGEADDRESS = "updateStorageAddress";

    public static final String FUNC_UPLOADFILE = "uploadFile";

    public static final Event FILECREATED_EVENT = new Event("FileCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected FileList(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FileList(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FileList(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FileList(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<FileCreatedEventResponse> getFileCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FILECREATED_EVENT, transactionReceipt);
        ArrayList<FileCreatedEventResponse> responses = new ArrayList<FileCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FileCreatedEventResponse typedResponse = new FileCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.fileInfo = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.dateInfo = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.storage_address = (String) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FileCreatedEventResponse> fileCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FileCreatedEventResponse>() {
            @Override
            public FileCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FILECREATED_EVENT, log);
                FileCreatedEventResponse typedResponse = new FileCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.fileInfo = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.dateInfo = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.storage_address = (String) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FileCreatedEventResponse> fileCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FILECREATED_EVENT));
        return fileCreatedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> fileCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FILECOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> fileNames(String param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FILENAMES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, String, String, String, String, String>> files(String param0, String param1, BigInteger param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FILES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Utf8String(param1), 
                new org.web3j.abi.datatypes.generated.Uint256(param2)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, String, String, String, String, String>>(function,
                new Callable<Tuple6<BigInteger, String, String, String, String, String>>() {
                    @Override
                    public Tuple6<BigInteger, String, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, String, String, String, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getFileChunkCount(String _filename) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFILECHUNKCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_filename)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getFileName(BigInteger startIndex, BigInteger endIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFILENAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(startIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(endIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getUserFileCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERFILECOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isExists(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateDateInfo(BigInteger _index, String _dateInfo) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEDATEINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new org.web3j.abi.datatypes.Utf8String(_dateInfo)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateFileInfo(BigInteger _index, String _fileInfo) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEFILEINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new org.web3j.abi.datatypes.Utf8String(_fileInfo)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateStorageAddress(BigInteger _index, String _storage_address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATESTORAGEADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new org.web3j.abi.datatypes.Utf8String(_storage_address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> uploadFile(String _name, String _fileInfo, String _dateInfo, String _storage_address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPLOADFILE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_fileInfo), 
                new org.web3j.abi.datatypes.Utf8String(_dateInfo), 
                new org.web3j.abi.datatypes.Utf8String(_storage_address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static FileList load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FileList(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FileList load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FileList(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static FileList load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FileList(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static FileList load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FileList(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<FileList> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(FileList.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<FileList> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FileList.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<FileList> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(FileList.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<FileList> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FileList.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class FileCreatedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String owner;

        public String name;

        public String fileInfo;

        public String dateInfo;

        public String storage_address;
    }
}
