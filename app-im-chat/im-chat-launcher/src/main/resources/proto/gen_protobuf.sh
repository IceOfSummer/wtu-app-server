protoc ./ChatMessage.proto --java_out=../../java
protoc ./MsgReceive.proto --java_out=../../java
protoc ./UserMessage.proto --java_out=../../java
protoc ./OnlineChatMessage.proto --java_out=../../java
# generate typescript file
mkdir "target"
pbjs --ts ./target/ChatMessage.ts ChatMessage.proto
pbjs --ts ./target/MsgReceive.ts MsgReceive.proto
pbjs --ts ./target/UserMessage.ts UserMessage.proto
pbjs --ts ./target/OnlineChatMessage.ts OnlineChatMessage.proto