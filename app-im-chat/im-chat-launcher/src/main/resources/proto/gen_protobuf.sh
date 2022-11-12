protoc ./ChatRequestMessage.proto --java_out=../../java
protoc ./ChatResponseMessage.proto --java_out=../../java
protoc ./ServerResponseMessage.proto --java_out=../../java
# generate typescript file
mkdir "target"
pbjs --ts ./target/ChatRequestMessage.ts ChatRequestMessage.proto
pbjs --ts ./target/ChatResponseMessage.ts ChatResponseMessage.proto
pbjs --ts ./target/ServerResponseMessage.ts ServerResponseMessage.proto
