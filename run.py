import subprocess
import os
import sys

def compile_and_run(java_files, main_class, args):
    script_dir = os.path.dirname(os.path.abspath(__file__))
    src_dir = os.path.join(script_dir, 'src')
    out_dir = os.path.join(script_dir, 'out')

    os.makedirs(out_dir, exist_ok=True)

    java_files = [os.path.join(src_dir, file) for file in java_files]

    compile_result = subprocess.run(['javac', '-d', out_dir] + java_files, capture_output=True, text=True)

    if compile_result.returncode != 0:
        print("Compilation failed:")
        print(compile_result.stderr)
        return

    run_result = subprocess.run(
        ['java', '-cp', out_dir, main_class] + args,
        capture_output=True, text=True
    )

    if run_result.returncode != 0:
        print("Execution failed:")
        print(run_result.stderr)
        return

    print(run_result.stdout)

java_files = [
    'Assertions.java',
    'Example.java',
    'MyBeautifulTestSuite.java',
    'TestIt.java',
    'TestItEngine.java',
    'TestResult.java'
]

main_class = 'TestItEngine'

args = sys.argv[1:]

compile_and_run(java_files, main_class, args)
