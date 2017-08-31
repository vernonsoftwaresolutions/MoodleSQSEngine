#!/bin/bash

#Get output values, this is a soemwhat naive approach since it is a lot of api calls


ecscluster=`aws cloudformation describe-stacks --stack-name vpctenant1 --query "Stacks[0].[Outputs[? starts_with(OutputKey, 'ecscluster')]][0][*].{OutputValue:OutputValue}" --output text`
ecslbarn=`aws cloudformation describe-stacks --stack-name vpctenant1 --query "Stacks[0].[Outputs[? starts_with(OutputKey, 'ecslbarn')]][0][*].{OutputValue:OutputValue}" --output text`
ecslbdnsname=`aws cloudformation describe-stacks --stack-name vpctenant1 --query "Stacks[0].[Outputs[? starts_with(OutputKey, 'ecslbdnsname')]][0][*].{OutputValue:OutputValue}" --output text`
ecslbhostedzoneid=`aws cloudformation describe-stacks --stack-name vpctenant1 --query "Stacks[0].[Outputs[? starts_with(OutputKey, 'ecslbhostedzoneid')]][0][*].{OutputValue:OutputValue}" --output text`
alblistener=`aws cloudformation describe-stacks --stack-name vpctenant1 --query "Stacks[0].[Outputs[? starts_with(OutputKey, 'alblistener')]][0][*].{OutputValue:OutputValue}" --output text`

