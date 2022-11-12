protoc ./ChatRequestMessage.proto --java_out=../../java
protoc ./ChatResponseMessage.proto --java_out=../../java
protoc ./ServerResponseMessage.proto --java_out=../../java
protoc ./SyncRequestMessage.proto --java_out=../../java
protoc ./ReceiveStatusMessage.proto --java_out=../../java
# generate typescript file for app
mkdir "target"
pbjs --ts ./target/ChatRequestMessage.ts ChatRequestMessage.proto
pbjs --ts ./target/ChatResponseMessage.ts ChatResponseMessage.proto
pbjs --ts ./target/ServerResponseMessage.ts ServerResponseMessage.proto
pbjs --ts ./target/SyncRequestMessage.ts SyncRequestMessage.proto
pbjs --ts ./target/ReceiveStatusMessage.ts ReceiveStatusMessage.proto
