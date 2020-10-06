import shutil
import sys
import argparse

#  example:  python makeZip.py ./a03/a3 ./a03/a3 .

def process_args():
    parser = argparse.ArgumentParser()
    parser.add_argument("archiveName", help="archiveName")
    parser.add_argument("root_dir", help="root_dir")
    parser.add_argument("base_dir", help="base_dir")
    args = parser.parse_args()
    print(type(args))
    return(args)
    # print args.square**2

args = process_args()
print(args)


shutil.make_archive(args.archiveName, 'zip', root_dir=args.root_dir, base_dir=args.base_dir)